import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.markers.None;
import org.knowm.xchart.SwingWrapper;

public class ComplexProjectileMotionSimulation {

    public static void main(String[] args) {
        double initialVelocity = 30.0;  // m/s
        double launchAngle = 30.0;  // degrees
        final double GRAVITY = 9.81;  // m/s^2
        final double AIR_DENSITY = 1.2;  // kg/m^3 (typical value at sea level)
        final double DRAG_COEFFICIENT = 0.47;  // For a sphere (approximate value)

        // Calculate components of initial velocity
        double initialVelocityX = initialVelocity * Math.cos(Math.toRadians(launchAngle));
        double initialVelocityY = initialVelocity * Math.sin(Math.toRadians(launchAngle));

        double time = 0.0;
        double x = 0.0;
        double y = 0.0;
        double vx = initialVelocityX;
        double vy = initialVelocityY;

        // Create a chart
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Projectile Motion with Air Resistance")
                .xAxisTitle("Horizontal Distance (m)")
                .yAxisTitle("Vertical Distance (m)")
                .build();

        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setMarkerSize(0);
        chart.getStyler().setYAxisMin(0);

        // Simulate projectile motion and add data to chart
        while (y >= 0) {
            double v = Math.sqrt(vx * vx + vy * vy);
            double dragForce = 0.5 * AIR_DENSITY * v * v * DRAG_COEFFICIENT;
            double ax = -dragForce / v * vx;
            double ay = -GRAVITY - dragForce / v * vy;

            vx += ax * TIME_STEP;
            vy += ay * TIME_STEP;

            x += vx * TIME_STEP;
            y += vy * TIME_STEP;

            chart.addSeries("Trajectory", new double[]{x}, new double[]{y}).setMarker(new None());

            time += TIME_STEP;
        }

        new SwingWrapper<>(chart).displayChart();
    }
}
