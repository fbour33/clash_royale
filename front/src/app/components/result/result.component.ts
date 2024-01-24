import {Component, Input} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {StatisticsService} from "../../services/statistics.service";
import {Card} from "../../models/card";
import {Deck} from "../../../assets/deck-manager";

@Component({
  selector: 'app-result',
  standalone: true,
  imports: [
    NgForOf,
    NgIf
  ],
  templateUrl: './result.component.html',
  styleUrl: './result.component.css'
})
export class ResultComponent {
  @Input() ngrams: string ='';
  ngramStats: any[] = [];

  constructor(private statsService: StatisticsService) {}

  ngOnInit() {
    console.log("test todo get ngrams value");
  }

}
