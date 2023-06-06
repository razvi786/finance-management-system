import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Vendor } from '../models/Vendor.model';

@Injectable({
  providedIn: 'root',
})
export class VendorService {
  path: string = environment.apiUrl + '/ems';

  vendorsPath: string = this.path + '/vendors';

  constructor(private http: HttpClient) {}

  getAllVendors(): Observable<Vendor[]> {
    return this.http.get<Vendor[]>(this.vendorsPath);
  }

  getVendorById(id: string): Observable<Vendor> {
    return this.http.get<Vendor>(this.path + '/vendor/' + id);
  }

  createVendor(vendor: Vendor): Observable<Vendor> {
    return this.http.post<Vendor>(this.path + '/vendor', vendor);
  }

  updateVendor(id: string, vendor: Vendor): Observable<Vendor> {
    return this.http.put<Vendor>(this.path + '/vendor/' + id, vendor);
  }
}
