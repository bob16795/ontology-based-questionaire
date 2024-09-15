<script>
	import { User } from 'lucide-svelte';
</script>

<div class="wrap">
	<h1>Select a view:</h1>
	<div class="flex">
		<a href="/doctor">
			<User size={18} />
			Doctor
		</a>
		<a href="/patient">
			<User size={18} />
			Patient
		</a>
	</div>
</div>

<style>
	div.wrap {
		padding: 1rem;
		background-color: #e6f7fa;
		width: 85%;
		max-width: 500px;
		border-radius: 0.75rem;
	}
	div h1 {
		margin: 0;
		font-weight: 300;
	}
	.flex {
		display: flex;
		justify-content: center;
		margin-top: 1rem;
		gap: 1rem;
	}
	a {
		display: flex;
		gap: 0.25rem;
		text-decoration: none;
		border-radius: 0.75rem;
		width: 100%;
		max-width: 200px;
		color: #274156;
		height: 50px;
		padding: 1rem;
		background: #fff;
		transition: box-shadow 0.3s ease;
		box-shadow: 0px 0px 0px rgba(0, 0, 0, 0);
	}
	a:hover {
		box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
	}

	@media screen and (max-width: 600px) {
		div.flex {
			flex-direction: column;
			align-items: center;
		}
		a {
			max-width: 90%;
		}
	}
</style>
