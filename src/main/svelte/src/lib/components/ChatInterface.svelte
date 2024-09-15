<script lang="ts">
	import { questions } from '$lib/stores';
	import { onMount } from 'svelte';
	import Question from './Question.svelte';

	onMount(async () => {
		questions.set([]);
		const req = await fetch('http://localhost:8080/get_question');
		const jose = await req.json();
		questions.update((q) => {
			q.push(jose);
			return q;
		});
	});
</script>

<div class="wrap">
	<h1>Health Questionnaire</h1>
	<hr />
	<div class="scroll">
		{#each $questions as question}
			<Question {question} />
		{/each}
	</div>
</div>

<style>
	.wrap {
		background-color: #e6f7fa;
		padding: 1rem;
		width: 100%;
		max-width: 800px;
		height: 100%;
		border-radius: 1rem;
	}
	div {
		overflow-y: scroll;
	}
	div h1 {
		margin: 0;
		font-weight: 300;
	}
</style>
