"use client"

import { useTheme } from "next-themes"
import { useEffect, useState } from "react"
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
} from "recharts"
import { MonthlyData } from "@/types/finance"

interface DashboardChartProps {
  data: MonthlyData[]
}

export function DashboardChart({ data }: DashboardChartProps) {
  const { theme, resolvedTheme } = useTheme()
  const [mounted, setMounted] = useState(false)

  // Ensure component is mounted to avoid hydration mismatch
  useEffect(() => {
    setMounted(true)
  }, [])

  const isDark = mounted && (theme === 'dark' || resolvedTheme === 'dark')

  // Theme-aware colors
  const colors = {
    income: isDark ? "#34d399" : "#10b981", // brighter green for dark mode
    expenses: isDark ? "#f87171" : "#ef4444", // brighter red for dark mode
    grid: isDark ? "#374151" : "#e5e7eb",
    text: isDark ? "#9ca3af" : "#6b7280",
    background: isDark ? "#1e293b" : "#ffffff",
    border: isDark ? "#475569" : "#e5e7eb",
  }

  if (!data || data.length === 0) {
    return (
      <div className="text-center text-muted-foreground p-4">
        No monthly data available
      </div>
    )
  }

  const CustomTooltip = ({ active, payload, label }: any) => {
    if (active && payload && payload.length) {
      return (
        <div className="bg-white dark:bg-slate-800 p-3 border dark:border-slate-600 rounded-lg shadow-sm">
          <p className="font-medium dark:text-gray-100">{label}</p>
          <p className="text-sm text-emerald-600 dark:text-emerald-400">
            Income: ₹{payload[0].value.toLocaleString('en-IN', { minimumFractionDigits: 2 })}
          </p>
          <p className="text-sm text-rose-600 dark:text-rose-400">
            Expenses: ₹{payload[1].value.toLocaleString('en-IN', { minimumFractionDigits: 2 })}
          </p>
        </div>
      )
    }
    return null
  }

  return (
    <div className="h-[300px] w-full">
      <ResponsiveContainer width="100%" height="100%">
        <LineChart
          data={data}
          margin={{
            top: 5,
            right: 30,
            left: 20,
            bottom: 5,
          }}
        >
          <CartesianGrid strokeDasharray="3 3" stroke={colors.grid} />
          <XAxis
            dataKey="month"
            tick={{ fontSize: 12, fill: colors.text }}
            stroke={colors.grid}
          />
          <YAxis
            tick={{ fontSize: 12, fill: colors.text }}
            stroke={colors.grid}
            tickFormatter={(value) => `₹${value.toLocaleString('en-IN', { maximumFractionDigits: 0 })}`}
          />
          <Tooltip content={<CustomTooltip />} />
          <Line
            type="monotone"
            dataKey="income"
            stroke={colors.income}
            strokeWidth={2}
            dot={{ fill: colors.income }}
            activeDot={{ r: 8 }}
            isAnimationActive={false}
          />
          <Line
            type="monotone"
            dataKey="expenses"
            stroke={colors.expenses}
            strokeWidth={2}
            dot={{ fill: colors.expenses }}
            activeDot={{ r: 8 }}
            isAnimationActive={false}
          />
        </LineChart>
      </ResponsiveContainer>
    </div>
  )
}
