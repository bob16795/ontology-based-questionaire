<script lang="ts">
	import { createEventDispatcher } from 'svelte';

	export let choice: string;
	export let disabled: boolean = false;
	export let picked: boolean = false;

	const dispatch = createEventDispatcher();

	export const click = async () => {
		picked = true;
		dispatch('click');

		const res = await fetch('http://localhost:8080/message', {
			method: 'POST',
      headers: {
        "Content-Type": "application/json",
      },
			body: JSON.stringify({
				msg: choice
			})
		});

		const json = await res.json();
	};
</script>

<button on:click={click} {disabled} class:disabled class:picked>
	{choice}
</button>

<style>
	button {
		appearance: none;
		border: 0;
		font-size: 16px;
		border-radius: 10px;
		min-width: 75px;
		padding: 0px 10px;
		height: calc(100% - 20px);
		background-color: rgba(230, 247, 250, 0.6);
		transition: all 0.2s ease;
		margin: 10px 0px;
		cursor: pointer;
	}

	button:hover {
		background-color: rgba(230, 247, 250, 1);
	}
	button:active:not(:disabled) {
		filter: brightness(0.95);
	}
	button:disabled {
		cursor: default;
		background-color: rgba(230, 247, 250, 0.6);
		color: #d8d5d4;
	}
	button.picked {
		background-color: rgb(39, 65, 86) !important;
		color: #e6f7fa !important;
	}
</style>
