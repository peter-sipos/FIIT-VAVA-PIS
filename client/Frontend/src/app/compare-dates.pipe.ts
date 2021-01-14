import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'compareDates'
})
export class CompareDatesPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    const dateStringParts = value.split(".");
    const dateNumParts = dateStringParts.map(p => parseInt(p));
    const dateVal = dateNumParts[0] + dateNumParts[1] * 100 + dateNumParts[2];
  
    return null;
  }

}
