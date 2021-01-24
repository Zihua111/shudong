<!-- 
	description:主页底部fixed部分 作用: 弹出新建框
	author:lyz
	date:2020/11/8
	version:1.0
 -->

<template>
	<view>
		<view style="display: flex;justify-content: center; opacity: 0.8;">
			<view class="bottom"></view>
			<view class="outCircle"></view>
		</view>
		<view style="display: flex;justify-content: center;">
			<view class="innerCircle" @click="popCreateNew"></view>
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
		mounted(){

		},
		methods: {
			//弹出新建框
			popCreateNew() {
				if (this.getGlobalUserInfo() == "" || this.getGlobalUserInfo() == null) {
					console.log('还未登入呢');
					return;
				};
				console.log("弹出新建框");
				uni.navigateTo({
					url: '/pages/create-new/create-new'
				});
			}
		}
	}
</script>

<style scoped>
	.bottom {
		background: #A17BA9;
		width: 100%;
		height: 66px;
		position: fixed;
		bottom: 0;
		border-top-left-radius: 10px;
		border-top-right-radius: 10px;
	}

	.outCircle {
		background: #A17BA9;
		width: 83px;
		height: 83px;
		border-radius: 50px;
		position: fixed;
		bottom: 27px;
	}

	.innerCircle {
		background: #DCE5E8 url(../static/userMainIcon/pencil.png) no-repeat center/60%;
		width: 55px;
		height: 55px;
		border: 3px solid rgb(34, 21, 51);
		border-radius: 50px;
		position: fixed;
		bottom: 37px;
	}
</style>
