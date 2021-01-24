<!--
	Description: switch button
	Author: Desmond
	Date:
	Version:
-->

<template>
	<view class="switch">

		<view class="switch-left" :class="{ chosen : status == 0}" @tap="changeStatus(0)">
			一颗胡萝卜
		</view>

		<view class="switch-right" :class="{ chosen : status != 0}" @tap="changeStatus(1)">
			匿名
		</view>

		<!-- <view class="bg-of-switch" :style="{'left':status == 0 ? '-13px;' :'41px' ,}"></view> -->
		<view class="bg-of-switch" :style="{left: (status == 0 ? (bgSwitchLeft + 'px') : (bgSwitchRight + 'px'))}"></view>
	</view>
</template>

<script>
	export default {
		props: {
			// options: ["一颗胡萝卜", "匿名"], //非必填，默认关/开
			// bgSwitchLeft: 0,
			// bgSwitchRight: 50
		},
		data() {
			return {
				status: 0,
				bgSwitchLeft: 0,
				bgSwitchRight: 125
			};
		},
		mounted() {
		},
		methods: {
			/**
			 * @onChange status 有效值 0/1
			 */
			changeStatus(status) {
				if (this.status != status) {
					this.status = status;
					this.$emit("onChange", (status == 0 ? '一颗胡萝卜' : '匿名'));
				}
			},
		}

	}
</script>

<style>
	.switch {
		background: #4f4f4f;
		border-radius: 75px;
		width: 550upx;
		float: right;
		line-height: 28px;
		display: flex;
		vertical-align: middle;
		position: relative;
		justify-content: space-between;
	}

	.switch-right,
	.switch-left {
		color: #ececec;
		opacity: 0.8;
		letter-spacing: 2upx;
		font-size: 10px;
		font-weight: 400;
		line-height: 22px;
		display: inline-block;
		align-items: center;
		justify-content: center;
		z-index: 30;
		width: 300upx;
		text-align: center;
		transition: padding-left 200ms,
			padding-right 200ms,
			font-size 200ms,
			font-weight 200ms,
			color 200ms;
		transition-delay: 0ms;
	}

	.bg-of-switch {
		height: 45upx;
		width: 300upx;
		background: #595959;
		border-radius: 75px;
		box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.16);
		z-index: 20;
		position: absolute;
		left: 0px;
		top: 0px;
		transition: left 500ms ease;
	}

	.chosen {
		font-weight: bold;
		font-size: 12px;
		color: #FFFFFF;
		opacity: 0.8;
		letter-spacing: 2upx;
		width: 300upx;
		display: flex;
		justify-content: center;
		align-items: center;
		transition: padding-left 300ms,
			padding-right 300ms,
			font-size 300ms,
			font-weight 300ms,
			color 300ms;
		transition-delay: 100ms;
	}
</style>
