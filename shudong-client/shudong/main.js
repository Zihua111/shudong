import Vue from 'vue'
import Vuex from 'vuex'
import App from './App'
import userUtil from 'common/userUtil.js' // 用户信息维护相关方法

App.mpType = 'app'

const app = new Vue({
	...App
})
app.$mount()

Vue.use(Vuex)
Vue.config.productionTip = false
Vue.prototype.test = function() {
	console.log("testing")
}

Vue.prototype.$serverUrl = "http://localhost:8080"
//Vue.prototype.$serverUrl = "https://www.checkchack.cn:8443/nottinghome"



Vue.prototype.setGlobalUserInfo = function(user) {
	// uni.setStorageSync('userInfo', user);
	userUtil.setGlobalUserInfo(user);
}

/**
 * 设置当前用户信息
 */
Vue.prototype.getGlobalUserInfo = function() {
	// var value = uni.getStorageSync('userInfo');
	// return value;
	return userUtil.getGlobalUserInfo();
}

/**
 * 清空当前用户信息
 */
Vue.prototype.removeGlobalUserInfo = function() {
	// uni.removeStorageSync('userInfo');
	userUtil.removeGlobalUserInfo();
}
