<template>
	<view>
		<camp-login @loginSuccess="navigate">登录</camp-login>
	</view>
</template>

<script>
	import CampLogin from '@/components/camp-login/camp-login.vue'
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			navigate() {
				uni.navigateTo({
				    url: '/pages/Lv/user-main-page'
				});
			}
		},
		components: {
			CampLogin,
		},
	}
</script>

<style>
	page {
		background-color: #333333;
	}
</style>
