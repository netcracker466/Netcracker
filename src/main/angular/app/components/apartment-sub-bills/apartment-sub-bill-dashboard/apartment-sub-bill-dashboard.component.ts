import {Component, OnInit} from '@angular/core';
import {ApartmentSubBill} from "../../../models/apartment-sub-bill";
import {ApartmentSubBillService} from "../../../services/apartment-sub-bill.service";
import {TokenStorageService} from "../../../services/token-storage.service";

class single {
    name?: String;
    value?: Number;
}

@Component({
    selector: 'app-apartment-sub-bill-dashboard',
    templateUrl: './apartment-sub-bill-dashboard.component.html',
    styleUrls: ['./apartment-sub-bill-dashboard.component.scss']
})
export class ApartmentSubBillDashboardComponent implements OnInit {

    subbills: ApartmentSubBill[] = [];
    dept: number = 0;

    constructor(public subbillsSevice: ApartmentSubBillService, public token: TokenStorageService) {
    }

    ngOnInit():
        void {
        this.subbillsSevice.getApartmentSubBillList().subscribe(
            data => {
                this.subbills = data
                var j;
                var single: single[] = [];
                for (j in this.subbills) {
                    single[j] = {name: this.subbills[j].communalUtility.name, value: this.subbills[j].balance};
                    this.dept = this.dept + this.subbills[j].debt;
                }
                Object.assign(this, {single});

            });
    }

    single: any[];
    view: any[] = [500, 350];

// options
    gradient: boolean = true;
    showLegend: boolean = true;
    showLabels: boolean = true;
    isDoughnut: boolean = false;
    legendPosition: string = 'below';
    title="Balance";

    colorScheme = {
        domain: ['#5B5F97', '#C2E812', '#F1DB48', '#FC7753']
    };


    onSelect(data): void {
        console.log('Item clicked', JSON.parse(JSON.stringify(data)));
    }

    onActivate(data):void {
        console.log('Activate', JSON.parse(JSON.stringify(data)));
    }

    onDeactivate(data):void {
        console.log('Deactivate', JSON.parse(JSON.stringify(data)));
    }
}
