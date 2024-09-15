<script>
	import { HouseIcon } from 'lucide-svelte';
</script>

<header>
	<a href="/">
		<HouseIcon />
	</a>
</header>

<style>
	a {
		display: flex;
		justify-content: center;
		margin: 7px;
		align-items: center;
		border-radius: 5px;
		height: 28px;
		width: 28px;
		color: #e6f7fa;
		transition: background-color 0.2s ease;
	}
	a:hover {
		background-color: rgba(255, 255, 255, 0.25);
	}
	header {
		display: flex;
		align-items: center;
		height: 3rem;
		background-color: #605856;
	}
</style>
