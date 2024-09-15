<script>
	import ChatInterface from '$lib/components/ChatInterface.svelte';
	import HeaderBar from '$lib/components/HeaderBar.svelte';
</script>

<HeaderBar />

<div>
	<ChatInterface />
</div>

<style>
	:global(body) {
		height: 100vh;
	}
	div {
		height: calc(100% - 3rem - 1rem * 2);
		padding: 1rem;
		display: flex;
		flex-direction: column;
		align-items: center;
	}
</style>
