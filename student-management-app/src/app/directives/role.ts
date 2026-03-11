import { Directive } from '@angular/core';

@Directive({
  selector: '[appRole]',
  standalone: false,
})
export class Role {
  constructor() {}
}
