import { Component } from '@angular/core';
import {CardSelectionComponent} from "../card-selection/card-selection.component";

@Component({
  selector: 'app-ngrams',
  standalone: true,
  imports: [
    CardSelectionComponent
  ],
  templateUrl: './ngrams.component.html',
  styleUrl: './ngrams.component.css'
})
export class NgramsComponent {

}
