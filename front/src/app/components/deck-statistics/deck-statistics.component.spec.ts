import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeckStatisticsComponent } from './deck-statistics.component';

describe('DeckStatisticsComponent', () => {
  let component: DeckStatisticsComponent;
  let fixture: ComponentFixture<DeckStatisticsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DeckStatisticsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DeckStatisticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
