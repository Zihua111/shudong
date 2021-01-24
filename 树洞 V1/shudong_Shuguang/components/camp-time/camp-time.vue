<!--
	Description: time count down
	Author: Desmond
	Date:
	Version:
-->

<template>
	<view class="camp-time">
		<view class="camp-time-left">
			<view class="stamp">
				<image lazy-load="true" class="block" src="../../static/image/block.png"></image>
				<view class="number">{{hour}}</view>
			</view>
			<view class="text">小时</view>
			<view class="stamp">
				<image lazy-load="true" class="block" src="../../static/image/block.png"></image>
				<view class="number">{{minute}}</view>
			</view>
			<view class="text">分后清空</view>
		</view>
		<view class="camp-time-right">
			<image lazy-load="true" class="clock" src="../../static/image/clock.png"></image>
			<image lazy-load="true" class="tem" src="../../static/image/tem.png"></image>
		</view>
	</view>
</template>

<script>
	export default {
		name: 'CampTime',
		data() {
			return {
				hour: 10,
				minute: 10
			};
		}
	}
</script>

<style scoped>
	@import url("./camp-time.css");
</style>
