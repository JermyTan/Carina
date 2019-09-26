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
  return R * c;
};

export const withinRadius = (
  carpark: Carpark,
  center: Point,
  radius: number
) => {
  return computeDistance(carpark, center) <= radius;
};
