import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService{
  constructor() { }

  public setItem(key: string, data: string): void {
    localStorage.setItem(key, data);
  }
  public setObject(data: any): void {
    let keys = Object.keys(data)
    keys.forEach(key => localStorage.setItem(key, data[key]))
  }
  public getItem(key: string): string | null {
      return localStorage.getItem(key)
  }
  public removeItem(key: string): void {
    localStorage.removeItem(key);
  }
}