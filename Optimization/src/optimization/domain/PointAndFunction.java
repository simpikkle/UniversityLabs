package optimization.domain;

public class PointAndFunction implements Comparable<PointAndFunction> {

    private Point point;

    private Double functionResult;

    public PointAndFunction(Point point, double functionResult) {
        this.point = point;
        this.functionResult = functionResult;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Double getFunctionResult() {
        return functionResult;
    }

    public void setFunctionResult(Double functionResult) {
        this.functionResult = functionResult;
    }

    @Override
    public int compareTo(PointAndFunction o) {
        return this.functionResult.compareTo(o.functionResult);
    }
}
