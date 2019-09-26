export type Carpark = {
  carparkId: string;
  address: string;
  subAddress: string;
  latitude: number;
  longitude: number;
  availableLots: string;
  distFromSrc: number;
};

export type Point = {
  lat: number;
  lng: number;
};
