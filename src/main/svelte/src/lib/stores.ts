import { writable } from "svelte/store";

export const questions = writable<Question[]>([
  {
    question: "hello 1",
    choices: ["1", "2", "3", "4"]
  },
  {
    question: "hello 2",
    choices: ["1", "2", "3", "4"]
  }
]);