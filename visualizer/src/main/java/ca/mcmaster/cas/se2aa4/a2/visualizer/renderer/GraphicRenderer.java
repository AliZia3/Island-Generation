package ca.mcmaster.cas.se2aa4.a2.visualizer.renderer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.visualizer.renderer.properties.ColorProperty;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class GraphicRenderer implements Renderer {

    private int THICKNESS = 3;

    public void render(Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.2f);
        canvas.setStroke(stroke);
        drawPolygons(aMesh, canvas);
        drawCentroids(aMesh, canvas); // Superimpose centroids
    }

    private void drawPolygons(Mesh aMesh, Graphics2D canvas) {
        for (Structs.Polygon p : aMesh.getPolygonsList()) {
            drawAPolygon(p, aMesh, canvas);
        }
    }

    private void drawAPolygon(Structs.Polygon p, Mesh aMesh, Graphics2D canvas) {
        Hull hull = new Hull();
        for (Integer segmentIdx : p.getSegmentIdxsList()) {
            Structs.Segment s = aMesh.getSegments(segmentIdx);
            Optional<Color> fill = new ColorProperty().extract(s.getPropertiesList());
            if (fill.isPresent()) {
                canvas.setColor(fill.get());
                Stroke stroke = new BasicStroke(10);
                canvas.setStroke(stroke);
                Vertex v1 = aMesh.getVertices(s.getV1Idx());
                Vertex v2 = aMesh.getVertices(s.getV2Idx());
                canvas.drawLine((int) v1.getX(), (int) v1.getY(), (int) v2.getX(), (int) v2.getY());
            }
            Stroke stroke = new BasicStroke(0.2f);
            canvas.setStroke(stroke);
            hull.add(aMesh.getSegments(segmentIdx), aMesh);
        }
        Path2D path = new Path2D.Float();
        Iterator<Vertex> vertices = hull.iterator();
        Vertex current = vertices.next();
        path.moveTo(current.getX(), current.getY());
        while (vertices.hasNext()) {
            current = vertices.next();
            path.lineTo(current.getX(), current.getY());
        }
        path.closePath();
        canvas.draw(path);
        Optional<Color> fill = new ColorProperty().extract(p.getPropertiesList());
        if (fill.isPresent()) {
            Color old = canvas.getColor();
            canvas.setColor(fill.get());
            canvas.fill(path);
            canvas.setColor(old);
        }
    }

    private void drawCentroids(Structs.Mesh aMesh, Graphics2D canvas) {
        for(Structs.Polygon p : aMesh.getPolygonsList()) {
            Structs.Vertex c = aMesh.getVertices(p.getCentroidIdx());
            THICKNESS = extractThickness(c.getPropertiesList());
            // System.out.println(THICKNESS);

            Optional<Color> fill = new ColorProperty().extract(c.getPropertiesList());
            if(fill.isPresent()) {
                Ellipse2D circle = new Ellipse2D.Float((float) c.getX()-1.5f, (float) c.getY()-1.5f, THICKNESS, THICKNESS);
                Color old = canvas.getColor();
                canvas.setColor(fill.get());
                canvas.fill(circle);
                canvas.setColor(old);
            }
        }
    }

    private int extractThickness(List<Property> properties) {
        String val = "0";
            for (Property p : properties) {
                if (p.getKey().equals("thickness")) {
                    val = p.getValue();
                }
            }
        int thickness = Integer.parseInt(val);
        return thickness;
    }

    

}