<template>
	<view>
		<staff-login @staffId="navigate"></staff-login>
	</view>
</template>

<script>
	import StaffLogin from '@/components/staffLogin/staffLogin.vue'
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			navigate() {
				uni.navigateTo({
				    url: '/pages/Lv/staff-main-page'
				});
			}
		},
		components: {
			StaffLogin,
		},
	}
</script>

<style>
	page {
		
	}
</style>
