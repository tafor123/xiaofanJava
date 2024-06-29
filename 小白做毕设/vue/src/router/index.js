import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/Manager.vue'
import Manager from "@/views/Manager.vue";

// 解决导航栏或者底部导航tabBar中的vue-router在3.0版本以上频繁点击菜单报错的问题。
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}
Vue.use(VueRouter)


const routes = [
  {
    path: '/',
    name: 'manager',
    component: Manager,
    redirect: '/home', //重定向
    children: [

      { path: 'home' ,name: 'Home' , meta:{name: '系统首页'}, component: () => import('../views/manager/Home.vue')},
      { path: 'user',name: 'User',meta:{name: '用户信息'},  component:() => import('../views/manager/User.vue')},
      { path:  '403', name: 'Auth',meta:{name: '无权限'},  component: ()=>import('../views/manager/Auth')},
      { path:  'person', name: 'Person',meta:{name: '个人信息'},  component: ()=>import('../views/manager/Person')},
      { path:  'password', name: 'Password',meta:{name: '修改密码'},  component: ()=>import('../views/manager/Password')},
    ]
  },
  {
    path: '/login',
    name: 'Login',
    meta:{name: '登录'},
    component: () =>import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    meta:{name: '注册'},
    component: () =>import('../views/Register.vue')
  },
  {
    path: '*',
    name: '404',
    meta:{name: '找不到页面'},
    component: () =>import('../views/404.vue')
  },



]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  // to 是到达的路由信息
  // from 是开源的路由信息
  // next 是帮助我们跳转路由的函数
  let adminPaths = ['/user']
  let user = JSON.parse(localStorage.getItem('honey-user') || '{}')

  if (user.role !== '管理员' && adminPaths.includes(to.path)) {
    // 如果当前登录的用户不是管理员，然后当前的到达的路径是管理员才有权限访问的路径，那这个时候我就让用户去到一个没有权限的页面，不让他访问实际的页面
    next('/403')
  } else {
    next()
  }
})

export default router
