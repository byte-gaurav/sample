package org.sample.dsa;

public class CourseCompletion {

    public static void main(String[] args) {
        CourseCompletion courseCompletion = new CourseCompletion();
        System.out.println(courseCompletion.canFinish(3, new int[][]{{2,1}, {1,0}}));
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Node[] numCourseMap = new Node[numCourses];
        boolean[] visited = new boolean[numCourses];
        for (int i = 0; i<numCourses; i++) {
            Node n = new Node(i);
            numCourseMap[i] = n;
        }
        for (int[] preRequisite : prerequisites) {
            numCourseMap[preRequisite[1]].dependency = numCourseMap[preRequisite[0]];
            if (visited[numCourseMap[preRequisite[0]].val]) {
                return false;
            }
            visited[numCourseMap[preRequisite[1]].val] = true;
        }
        return true;
    }

    class Node {
        public Node dependency;
        public int val;

        public Node(int val, Node dependency) {
            this(val);
            this.dependency = dependency;
        }

        public Node(int val) {
            this.val=val;
        }
    }
}
