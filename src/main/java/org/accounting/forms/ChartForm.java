package org.accounting.forms;

import org.accounting.database.models.User;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.util.ArrayList;

class ChartForm extends JFrame {

    ChartForm() {
        createForm();
    }

    private void createForm() {
        setTitle("Chart");
        setSize(800, 600);
        setLocationRelativeTo(null);

        CategoryDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(chartPanel);
    }

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        ArrayList<User> results = User.getAll();

        for (User user: results) {
            dataset.setValue((int) user.getTimeInProgram() / 60 % 60, "Time in program", user.getEmail());
        }

        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {

        return ChartFactory.createBarChart(
                "Time in program",
                "Users",
                "Time in minutes",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);
    }
}
