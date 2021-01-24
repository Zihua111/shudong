<!-- 
	description:主页底部fixed部分 作用: 跳转到新建页
	author:lyz
	date:2020/11/8
	version:1.0
 -->

<template>
	<view>
		<view style="display: flex;justify-content: center;opacity: 0.5;">
			<view class="bottom"></view>
			<view class="outCircle"></view>
		</view>
		<view style="display: flex;justify-content: center;">
			<view class="innerCircle" @click="goCreateNew"></view>
		</view>
	</view>
</template>

<script>
	export default {
		components: {

		},
		data() {
			return {

			}
		},
		methods: {
			//跳转到新建页
			goCreateNew() {
				uni.navigateTo({
					url: '/pages/Lou/create-new/create-new'
				});
			}
		}
	}
</script>

<style scoped>
	.bottom {
		background: white;
		width: 100%;
		height: 66px;
		position: fixed;
		bottom: 0;
		border-top-left-radius: 10px;
		border-top-right-radius: 10px;
	}

	.outCircle {
		background: white;
		width: 83px;
		height: 83px;
		border-radius: 50px;
		position: fixed;
		bottom: 27px;
	}

	.innerCircle {
		background: white;
		width: 55px;
		height: 55px;
		border-radius: 50px;
		position: fixed;
		bottom: 37px;
	}
</style>
