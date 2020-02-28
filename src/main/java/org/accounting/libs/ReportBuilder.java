package org.accounting.libs;

import java.util.ArrayList;
import java.util.Arrays;

public class ReportBuilder {
    public static int html = 0x01;
    public static int pdf = 0x02;

    interface Node {
        String buildNode();
    }

    private ArrayList<Node> nodes = new ArrayList<>();

    public void addTableNode(TableNode node) {
        nodes.add(node);
    }

    public void addTextNode(TextNode node) {
        nodes.add(node);
    }

    public String buildReport(int outputStyle) {
        StringBuilder builder = new StringBuilder();

        for(Node n: nodes) {
            builder.append(n.buildNode());
        }

//        if ((outputStyle & ReportBuilder.html) > 0) {
//            HTMLOutputBuilder pdfBuilder = new HTMLOutputBuilder(nodes);
//            pdfBuilder.buildReport();
//        }
//
//        if ((outputStyle & ReportBuilder.pdf) > 0) {
//            PdfOutputBuilder pdfBuilder = new PdfOutputBuilder(nodes);
//            pdfBuilder.buildReport();
//            pdfBuilder.saveFile();
//        }


        return builder.toString();
    }

    static class HtmlHelper {
        String wrap(Object element, String tag) {
            return wrap(element, tag, "");
        }

        String wrap(Object element, String tag, String style) {
            return '<' + tag + " style=\"" + style + "\">" + element + "</" + tag + '>';
        }
    }

    public static class TextNode implements Node {
        String text;
        String style = "";
        HtmlHelper helper = new HtmlHelper();

        public TextNode(String text) {
            this.text = text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setStyle(String style) {
            this.style = style;
        }

        @Override
        public String buildNode() {
            String textStyle = style.isEmpty() ? "" : style;

            return helper.wrap(text, "div", style);
        }
    }


    public static class TableNode implements Node {
        private String title = "";
        private ArrayList<String> headers;
        private ArrayList<Object[]> data = new ArrayList<>();
        private String style = "";
        private HtmlHelper helper = new HtmlHelper();

        public TableNode(String[] headers) {
            this.headers = new ArrayList<>(Arrays.asList(headers));
        }

        public TableNode(String[] headers, Object[][] data) {
            this(headers);
            this.data.addAll(Arrays.asList(data));
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setTableStyle(String style) {
            this.style = style;
        }

        @Override
        public String buildNode() {
            StringBuilder builder = new StringBuilder();

            if (!title.isEmpty()) {
                builder.append(helper.wrap(title, "p"));
            }

            StringBuilder headerRow = new StringBuilder();
            for (String header: headers) {
                headerRow.append(helper.wrap(header, "th", "border: solid black"));
            }

            StringBuilder rows = new StringBuilder();
            for (Object[] datum : data) {
                StringBuilder row = new StringBuilder();

                for (int column = 0; column < headers.size(); column++) {
                    if (column >= datum.length) { continue; }

                    String cell;

                    if (datum[column] instanceof Node) {
                        cell = ((Node)datum[column]).buildNode();
                    } else {
                        cell = String.valueOf(datum[column]);
                    }

                    row.append(helper.wrap(cell, "td", "border: solid black"));
                }

                rows.append(row);
            }

            String tableStyle = "";
            if (!style.isEmpty()) {
                tableStyle = style;
            } else {
                tableStyle = "width: 100%";
            }

            String headersRows = helper.wrap(headerRow.toString(), "tr", "height: 50px;") + rows.toString();

            builder.append(helper.wrap(headersRows, "table", tableStyle + "; border: solid black"));

            return helper.wrap(builder.toString(), "div");
        }

        public void appendRow(Object[] row) {
            this.data.add(row);
        }
    }
}
