import { Component, Input, AfterViewInit, OnChanges, SimpleChanges } from '@angular/core';
import Chart from 'chart.js/auto';
import { NgForOf, NgIf } from "@angular/common";
import { NgramService } from '../../services/ngram.service';

@Component({
  selector: 'app-result',
  standalone: true,
  imports: [NgForOf, NgIf],
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.css']
})
export class ResultComponent implements AfterViewInit, OnChanges {
  @Input() ngrams: string = '';
  title = 'ng-chart';
  chart: any = [];

  constructor(private ngramService: NgramService) {}

  ngOnChanges(changes: SimpleChanges) {
    if (changes['ngrams'] && changes['ngrams'].currentValue) {
      this.updateChart();
    }
  }


  private updateChart() {
    //Pour tester, remplacer this.ngrams par '070f143b4041576b' (dans notre ngrams.json)
    const { labels, data } = this.ngramService.getChartData('070f143b4041576b');
    this.chart = new Chart('myChart', {
      type: 'line',
      data: {
        labels: labels,
        datasets: [
          {
            label: 'Total win',
            data: data,
            borderWidth: 1,
            fill: false,
          },
        ],
      },
      options: {
        scales: {
          y: {
            beginAtZero: true,
          },
        },
      },
    });
  }

  ngAfterViewInit() {
    this.updateChart();
  }
}
