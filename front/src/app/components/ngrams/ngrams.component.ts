import { Component } from '@angular/core';
import {CardSelectionComponent} from "../card-selection/card-selection.component";
import {ResultComponent} from "../result/result.component";
import {Card} from "../../models/card";
import {Deck} from "../../../assets/deck-manager";

@Component({
  selector: 'app-ngrams',
  standalone: true,
  imports: [
    CardSelectionComponent,
    ResultComponent
  ],
  templateUrl: './ngrams.component.html',
  styleUrl: './ngrams.component.css'
})
export class NgramsComponent {
  selectedCards: Card[] = [];
  ngrams: string = '';

  onCardSelectionChanged(selectedCards: Card[]) {
    this.selectedCards = [...selectedCards];
    this.getNGram(selectedCards);
  }

  getNGram(list: Card[]){
        let deck = new Deck();
        list.map((value) => deck.addCard(value.id.toString()));
        this.ngrams = deck.toString();
    }

}
