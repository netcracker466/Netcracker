import {Component, OnInit} from '@angular/core';
import {Announcement} from "../../../models/announcement";
import {AnnouncementService} from "../../../services/announcement.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {HouseVotingService} from "../../../services/house-voting.service";
import {VotingOptionService} from "../../../services/voting-option.service";
import {HouseVoting} from "../../../models/house-voting";
import {VotingOption} from "../../../models/voting-option";

@Component({
    selector: 'app-announcements-create',
    templateUrl: './announcements-create.component.html',
    styleUrls: ['./announcements-create.component.css']
})
export class AnnouncementsCreateComponent implements OnInit {
    announcementFormGroup: FormGroup;
    announcement: Announcement = {
        title: '',
        body: '',
        isOpened: false,
    };

    votingFormGroup: FormGroup;
    hasVoting: boolean = false;
    houseVoting: HouseVoting = {
        title: ''
    };
    votingCount: number = 2;

    constructor(private announcementService: AnnouncementService,
                private houseVotingService: HouseVotingService,
                private votingOptionService: VotingOptionService,
                private router: Router) {}

    counter(i: number) {
        return new Array(i);
    }

    ngOnInit(): void {
        this.announcementFormGroup = new FormGroup({
            title: new FormControl('',[
                Validators.required,
                Validators.minLength(2),
                Validators.maxLength(255)
            ]),
            body:new FormControl('',Validators.maxLength(1023)),
            isOpened:new FormControl('',Validators.required)
        })

        this.votingFormGroup = new FormGroup({
            title: new FormControl('',[
                Validators.required,
                Validators.minLength(2),
                Validators.maxLength(255)
            ]),
        })

        for (let i = 0; i < this.votingCount; i++) {
            this.votingFormGroup.addControl(
                'name' + i,
                new FormControl('',[
                    Validators.required,
                    Validators.minLength(2),
                    Validators.maxLength(255)
                ])
            )
        }

        console.log(this.votingFormGroup);
    }

    saveAnnouncement(): void {
        this.announcementService.createAnnouncement(this.announcement)
            .subscribe(
                response => {
                    console.log(response);
                    if (this.hasVoting) {
                        this.houseVoting.announcement = response;
                        this.saveHouseVoting(response.announcementId);
                    }
                    else {
                        this.router.navigateByUrl('announcements');
                    }
                },
                error => {
                    console.log(error);
                });
    };

    addVotingOptionControl() {
        this.votingFormGroup.addControl(
            'name' + this.votingCount++,
            new FormControl('',[
                Validators.required,
                Validators.minLength(2),
                Validators.maxLength(255)
            ])
        )
    }

    deleteVotingOptionControl() {
        if (this.votingCount > 2) {
            this.votingFormGroup.removeControl('name' + (--this.votingCount));
        }

    }

    saveHouseVoting(announcementId: number): void {
        this.houseVotingService.createHouseVoting(announcementId, this.houseVoting)
            .subscribe(
                response => {
                    console.log(response);
                    for (let i = 0; i < this.votingCount; ++i) {
                        this.saveVotingOption(
                            announcementId,
                            this.votingFormGroup.value['name'+ i]
                    );
                    }
                },
                error => {
                    console.log(error);
                });
    }

    saveVotingOption(announcementId: number, votingOption:VotingOption): void {
        this.votingOptionService.createVotingOption(announcementId, votingOption)
            .subscribe(
                response => {
                    console.log(response);
                },
                error => {
                    console.log(error);
                });
    }
}
