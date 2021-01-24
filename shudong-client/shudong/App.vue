<script>
	export default {
		globalData: {
			text: 'text'
		},
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		},
		onError(err) {
			console.log(err)
		}
	}
</script>

<style>

</style>
