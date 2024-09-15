<script lang="ts">
	import { FileQuestion } from 'lucide-svelte';
	import QuestionChoice from './QuestionChoice.svelte';

	export let question: Question;

	let disabled: boolean;

	const click = () => {
		console.log('YIPPEE 2');
		disabled = true;
	};
</script>

<div class="block">
	<div class="icon-bg">
		<FileQuestion />
	</div>
	<div class="text">
		{question.question}
	</div>
	<div class="options">
		{#each question.choices as choice}
			<QuestionChoice {choice} bind:disabled on:click={click} />
		{/each}
	</div>
</div>

<style>
	.block {
		position: relative;
		background-color: #fff;
		border-radius: 5px;
		min-height: 75px;
	}
	.icon-bg {
		position: absolute;
		display: flex;
		align-items: center;
		justify-content: center;
		top: 5px;
		left: 5px;
		width: 35px;
		height: 35px;
		border-radius: 90px;
		background-color: rgba(230, 247, 250, 0.6);
	}
	.text {
		display: flex;
		align-items: center;
		margin-left: 45px;
		height: 40px;
		font-size: 1.1rem;
		font-weight: 500;
	}
	.options {
		display: flex;
		gap: 10px;
		position: absolute;
		top: 0;
		right: 10px;
		height: 100%;
	}
</style>
