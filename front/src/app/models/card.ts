export type Card = {
 name: string;
 id: number;
 maxLevel: number;
 elixir?: number;
 evolutionBoost?: number;
 iconUrls: {
    medium?: string,
    evo?: string
 }
}
