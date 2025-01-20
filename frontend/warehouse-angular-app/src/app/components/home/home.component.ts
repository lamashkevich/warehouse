import { Component, OnInit } from '@angular/core';
import { StatisticsService } from '../../services/statistics.service';
import { Statistics } from '../../models/statistics.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  statistics: Statistics = {
    totalPrice: 0,
    totalQuantity: 0,
    lastAdded: ""
  };
  
  showSuccessAlert = false;
  showErrorAlert = false;
  
  constructor(private statisticsService: StatisticsService) {}

  ngOnInit(): void {
    this.statisticsService.getStatistics().subscribe((data) => {
      this.statistics = data;
    });
  }
}
