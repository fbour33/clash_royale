import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NgramsComponent } from './ngrams.component';

describe('NgramsComponent', () => {
  let component: NgramsComponent;
  let fixture: ComponentFixture<NgramsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NgramsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NgramsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
