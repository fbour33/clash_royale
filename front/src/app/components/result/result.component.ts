import {Component, Input} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {StatisticsService} from "../../services/statistics.service";

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
  @Input() selectedCardIds: number[] = [];

  ngramStats: any[] = [];

  constructor(private statsService: StatisticsService) {}

  ngOnInit() {
    this.getNgramStats();
  }

  getNgramStats() {
    /*this.statsService.getStats().subscribe(
      data => {
        this.ngramStats = data;
      },
      error => {
        console.error('There was an error!', error);
      }
    );*/
  }
}
