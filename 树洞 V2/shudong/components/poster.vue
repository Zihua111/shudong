<!-- 
Description: poster
Author: XueshanChen
Date：2020//11/26
Version:1.0.0
 -->

<template>
	<view @longPress="getPhotosAlbumAuth">
		<uni-popup ref="popPoster" :maskClass="{'position': 'fixed',
			'bottom': 0,'top': 0,'left': 0,'right': 0,'backgroundColor': 'rgb(34, 21, 51, 0.6)'}">
			<canvas canvas-id='shareFriend' style='width:300px;height:340px;'></canvas>
		</uni-popup>
	</view>
</template>

<script>
	import uniPopup from '@/components/uni-popup/uni-popup.vue'
	export default {
		components: {
			uniPopup
		},
		data() {
			return {
				picTempUrl: '',
				posterStatus: false,
				posterInfo: {
					nickname: "",
					comment: "",
					staffIcon: ""
				}
			}
		},
		created() {
			uni.$on("share", (res) => {
				console.log(res);
				this.posterInfo = res;
				this.createCanvas();
				this.$refs.popPoster.open();
			});
		},
		methods: {
			//绘制海报
			createCanvas() {

				var ctx = uni.createCanvasContext('shareFriend', this);
				ctx.drawImage('../static/poster/bg.png', 0, 0, 300, 340); //画背景图

				//画头像
				ctx.save();
				var r = 25;
				var x = 20;
				var y = 20;
				var d = 2 * r;
				var cx = x + r;
				var cy = y + r;
				ctx.arc(cx, cy, r, 0, 2 * Math.PI);
				ctx.clip();
				ctx.drawImage(this.posterInfo.staffIcon, x, y, d, d);
				ctx.restore();

				//画二维码,这边可以变成小程序码
				ctx.save();
				var rCode = 45;
				var xCode = 10;
				var yCode = 200;
				d = 2 * rCode;
				cx = xCode + rCode;
				cy = yCode + rCode;
				ctx.arc(cx, cy, rCode, 0, 2 * Math.PI);
				ctx.clip();
				ctx.drawImage('../static/poster/tem.png', xCode, yCode, d, d);
				ctx.restore();


				//添加文字

				//user image
				ctx.setFontSize(14);
				ctx.setFillStyle('black');
				ctx.fillText(this.posterInfo.nickname, 90, 50);

				ctx.setFontSize(16);
				ctx.setFillStyle('black');
				ctx.fillText("适合我的解压方式是：", 40, 110);

				//提示文字
				ctx.setFontSize(14);
				ctx.setFillStyle('#F0AD4E');
				ctx.fillText('扫描二维码测测我的！', 190, 250);

				//开始画画完后生成新的临时图片地址
				let that = this;
				ctx.draw(false, ()=>{
					uni.canvasToTempFilePath({ //静静注意 这里有bug 我也改不来 你问问妖妖吧
						canvasId: 'shareFriend',
						success: (res) => {
							that.picTempUrl = res.tempFilePath;
							console.log(123);
						},
						complete: (res) => {
							console.log(res);
						}
					})
				})
			},
			//获取手机相册权限
			getPhotosAlbumAuth() {
				uni.getSetting({
					success: (res) => {
						if (!res.authSetting['scope.writePhotosAlbum']) {
							uni.authorize({
								scope: 'scope.writePhotosAlbum',
								success: () => {
									this.saveImage();
								}
							})
						} else {
							this.saveImage();
						}
					}
				})
			},
			//保存海报
			saveImage() {
				uni.saveImageToPhotosAlbum({
					filePath: this.picTempUrl,
					success: (data) => {
						uni.showToast({
							title: "图片保存成功",
							icon: "success",
							mask: true
						})
					},
					complete: () => {
						this.posterStatus = false;
					}
				})
			},
		}
	}
</script>

<style scoped>

</style>
