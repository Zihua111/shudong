<!-- 
	description:搜索页
	author:lyz
	date:2020/11/8
	version:1.0
 -->

<template>
	<view>
		<uni-search-bar :radius="10" maxlength="50" @input="goStaffMain"></uni-search-bar>
	</view>
</template>

<script>
	import uniSearchBar from './components/uni-search-bar.vue'
	export default {
		components: {
			uniSearchBar
		},
		data() {
			return {

			}
		},
		methods: {
			goStaffMain(data) {
				if (data.value == "yaoyao") {
					uni.redirectTo({
						url: '/pages/Lou/staff-main/staff-main'
					});
				}
			}
		}
	}
</script>

<style>

</style>
