<!-- 
	description:轮播图
	author:lyz
	date:2020/11/8
	version:1.0
 -->

<template>
	<view class="box">
		<swiper :indicator-dots="true" :autoplay="true" :interval="3000" :duration="1000" :circular="true" indicator-color="rgb(255,255,255)"
		 indicator-active-color="#00ff00">
			<block v-for="(item,index) in banner" :key="index">
				<swiper-item>
					<view class="swiper-item" id="swiper-item">
						<image :src="item.imageUrl" class="imageurl" @tap="_previewImage(item.imageUrl)" mode="widthFix"></image>
					</view>
				</swiper-item>
			</block>
		</swiper>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				banner: [{
						"imageUrl": "../static/customSwiper/img1.jpg"
					},
					{
						"imageUrl": "../static/customSwiper/img2.jpg"
					},
					{
						"imageUrl": "../static/customSwiper/img3.jpg"
					}
				]
			}
		},
		methods: {
			//预览图片
			_previewImage(imageUrl) {
				if (this.getGlobalUserInfo() == "" || this.getGlobalUserInfo() == null) {
					console.log('还未登入呢');
					return;
				};
				// var imgArr = [];
				// imgArr.push(imageUrl);
				// //预览图片
				// uni.previewImage({
				// 	urls: imgArr,
				// 	current: 0
				// });
			}
		}
	}
</script>

<style scoped>
	/* 轮播 */
	.box {
		margin-top: 33.5px;
		margin-left: 23.5px;
		margin-right: 26.5px;
		border-radius: 10px;
		overflow: hidden;
		background-color: #515151;
	}

	.imageurl {
		width: 100%;
		height: 300upx !important;
	}

	swiper {
		height: 300upx !important;
	}
</style>
