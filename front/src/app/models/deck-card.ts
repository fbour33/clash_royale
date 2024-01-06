export class DeckCard {
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
