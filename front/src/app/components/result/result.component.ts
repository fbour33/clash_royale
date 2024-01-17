import {Component, Input} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";

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

  constructor(private statsService: StatsService) {}

  ngOnInit() {
    this.getNgramStats();
  }

  getNgramStats() {
    // La méthode getStats devrait être définie dans votre StatsService pour récupérer les données
    this.statsService.getStats().subscribe(
      data => {
        this.ngramStats = data;
      },
      error => {
        console.error('There was an error!', error);
      }
    );
  }
}
