<!-- 
	description:轮播图
	author:lyz
	date:2020/11/8
	version:1.0
 -->

<template>
	<view>
		<!-- 轮播 -->
		<view class="box">
			<swiper :indicator-dots="true" :autoplay="true" :interval="3000" :duration="1000" :circular="true" indicator-color="rgb(255,255,255)"
			 indicator-active-color="#00ff00">
				<block v-for="(item,index) in banner" :key="index">
					<swiper-item>
						<view class="swiper-item" id="swiper-item" @click="localcont">
							<image :src="item.image" mode="aspectFill" class="imageurl"></image>
						</view>
					</swiper-item>
				</block>
			</swiper>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				banner: [{
						"image": "../../../../static/img1.jpg"
					},
					{
						"image": "../../../../static/img2.jpg"
					},
					{
						"image": "../../../../static/img3.jpg"
					}
				]
			}
		},
		methods:{
			localcont(){
				console.log("别点它");
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
