/*
文档描述：用户信息维护相关方法 
作者：@Jerrio
日期：2020.3.14
*/

/**
 * 获取当前用户信息（我）
 * @param {Object} user
 */
function setGlobalUserInfo(user) {
	uni.setStorageSync('userInfo', user);
}

/**
 * 设置当前用户信息
 */
function getGlobalUserInfo() {
	var value = uni.getStorageSync('userInfo');
	return value;
}

/**
 * 清空当前用户信息
 */
function removeGlobalUserInfo() {
	uni.removeStorageSync('userInfo');
}

export default{
	setGlobalUserInfo,
	getGlobalUserInfo,
	removeGlobalUserInfo
}