import React from "react";
import { ResponsiveBar } from "@nivo/bar";

// TODO: Use real data when backend is done.
export const HistogramData = [
  {
    hour: 0,
    timeLabel: "12m",
    lots: 53
  },
  {
    hour: 1,
    timeLabel: "1a",
    lots: 100
  },
  {
    hour: 2,
    timeLabel: "2a",
    lots: 19
  },
  {
    hour: 3,
    timeLabel: "3a",
    lots: 70
  },
  {
    hour: 4,
    timeLabel: "4a",
    lots: 100
  },
  {
    hour: 5,
    timeLabel: "5a",
    lots: 80
  },
  {
    hour: 6,
    timeLabel: "6a",
    lots: 100
  },
  {
    hour: 7,
    timeLabel: "7a",
    lots: 80
  },
  {
    hour: 8,
    timeLabel: "8a",
    lots: 70
  },
  {
    hour: 9,
    timeLabel: "9a",
    lots: 53
  },
  {
    hour: 10,
    timeLabel: "10a",
    lots: 100
  },
  {
    hour: 11,
    timeLabel: "11a",
    lots: 80
  },
  {
    hour: 12,
    timeLabel: "12p",
    lots: 70
  },
  {
    hour: 13,
    timeLabel: "1p",
    lots: 13
  },
  {
    hour: 14,
    timeLabel: "2p",
    lots: 70
  },
  {
    hour: 15,
    timeLabel: "3p",
    lots: 53
  },
  {
    hour: 16,
    timeLabel: "4p",
    lots: 70
  },
  {
    hour: 17,
    timeLabel: "5p",
    lots: 53
  },
  {
    hour: 18,
    timeLabel: "6p",
    lots: 70
  },
  {
    hour: 19,
    timeLabel: "7p",
    lots: 53
  },
  {
    hour: 20,
    timeLabel: "8p",
    lots: 70
  },
  {
    hour: 21,
    timeLabel: "9p",
    lots: 53
  },
  {
    hour: 22,
    timeLabel: "10p",
    lots: 70
  },
  {
    hour: 23,
    timeLabel: "11p",
    lots: 53
  }
];

const labelsToShow = ["6a", "9a", "12p", "3p", "6p", "9p", "12m", "3a"];
const colors = {
  lotsLots: "#7BAAF7",
  currLots: "#4285F4",
  lowLots: "#ff80ab",
  currLowLots: "#f50057"
};

const getBarColor = (bar: any) => {
  const hour = new Date().getHours();
  if (bar.value <= 20) {
    if (bar.data.hour === hour) {
      return colors.currLowLots;
    }
    return colors.lowLots;
  } else {
    if (bar.data.hour === hour) {
      return colors.currLots;
    }
    return colors.lotsLots;
  }
};

const HistogramChart = ({ data }: any) => {
  return (
    <ResponsiveBar
      data={data}
      keys={["lots"]}
      indexBy="timeLabel"
      margin={{ top: 10, right: 0, bottom: 30, left: 30 }}
      padding={0.1}
      colors={getBarColor}
      axisTop={null}
      axisRight={null}
      axisBottom={{
        tickSize: 0,
        format: v => (labelsToShow.find(vts => vts === v) ? v : ""),
        tickPadding: 5,
        tickRotation: 0
      }}
      axisLeft={{
        tickValues: 3,
        tickSize: 0,
        tickPadding: 5,
        tickRotation: 0
      }}
      labelSkipWidth={12}
      labelSkipHeight={12}
      labelTextColor={{ from: "color", modifiers: [["darker", 1.6]] }}
      enableLabel={false}
      enableGridY={false}
      motionStiffness={90}
      motionDamping={15}
      tooltip={({ id, value, color }) => (
        <strong style={{ color }}>{value} lots</strong>
      )}
    />
  );
};

export default HistogramChart;
