import { Carpark, Point } from "./Types";

export const computeDistance = (carpark: Carpark, center: Point) => {
  const point = {
    lat: carpark.latitude,
    lng: carpark.longitude
  } as Point;
  const R = 6371e3;
  const deg2rad = (n: number) => (n * Math.PI) / 180;

  const dLat = deg2rad(point.lat - center.lat);
  const dLon = deg2rad(point.lng - center.lng);

  const a =
    Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.cos(deg2rad(center.lat)) *
      Math.cos(deg2rad(point.lat)) *
      Math.sin(dLon / 2) *
      Math.sin(dLon / 2);
  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  return Math.trunc(R * c);
};

export const withinRadius = (
  carpark: Carpark,
  center: Point,
  radius: number
) => {
  return computeDistance(carpark, center) <= radius;
};

export const mapEntriesToCarparks = (
  entries: any[],
  hasDistFromSrc: boolean
) => {
  return entries.map((entry: any) => {
    return {
      carparkId: entry.carparkId,
      address: entry.development,
      subAddress: entry.area,
      latitude: parseFloat(entry.latitude),
      longitude: parseFloat(entry.longitude),
      availableLots: entry.lots.C,
      distFromSrc: hasDistFromSrc ? parseInt(entry.distFromSrc) : -1
    } as Carpark;
  });
};

export const updateCarparksDistFromSrc = (
  carparks: Carpark[],
  center: Point
) => {
  return carparks.map((carpark: Carpark) => {
    return {
      carparkId: carpark.carparkId,
      address: carpark.address,
      subAddress: carpark.subAddress,
      latitude: carpark.latitude,
      longitude: carpark.longitude,
      availableLots: carpark.availableLots,
      distFromSrc: Math.round(computeDistance(carpark, center))
    } as Carpark;
  });
};
