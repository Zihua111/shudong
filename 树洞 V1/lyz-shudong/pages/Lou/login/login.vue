<!-- 
	description:登入页
	author:lyz
	date:2020/11/8
	version:1.0
 -->

<template>
	<view>
		<view class="login-container">
			<camp-login loginText="登录" loginStyle="height:50px;width:200px;line-height:50px;" @loginSuccess="goUserMain"
			 @loginFail="login_fail"></camp-login>
		</view>
	</view>
</template>

<script>
	import CampLogin from './components/camp-login.vue'
	export default {
		components: {
			CampLogin
		},
		data() {
			return {

			}
		},
		methods: {
			goUserMain() {
				console.log("to user-main");
				uni.navigateTo({
					url: '/pages/Lou/user-main/user-main'
				});
			},
			login_fail() {
				console.log("登入失败");
			}
		}
	}
</script>

<style>
	.login-container {
		width: 100%;
		height: 100vh;
		display: flex;
		align-items: center;
		justify-content: center;
		background: url(../../../static/img3.jpg);
	}

	camp-login {
		position: fixed;
	}
</style>
