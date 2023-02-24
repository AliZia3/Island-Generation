# Assignment A2: Mesh Generator

-   Mahad Ahmed [ahmem73@mcmaster.ca]
-   Saad Salman [salmam12@mcmaster.ca]
-   Ali Zia [ziam8@mcmaster.ca]

## How to run the product

_This section needs to be edited to reflect how the user can interact with thefeature released in your project_

### Installation instructions

This product is handled by Maven, as a multi-module project. We assume here that you have cloned the project in a directory named `A2`

To install the different tooling on your computer, simply run:

```
mosser@azrael A2 % mvn install
```

After installation, you'll find an application named `generator.jar` in the `generator` directory, and a file named `visualizer.jar` in the `visualizer` one.

### Generator

To run the generator, go to the `generator` directory, and use `java -jar` to run the product. The product takes one single argument (so far), the name of the file where the generated mesh will be stored as binary.

```
mosser@azrael A2 % cd generator
mosser@azrael generator % java -jar generator.jar sample.mesh
mosser@azrael generator % ls -lh sample.mesh
-rw-r--r--  1 mosser  staff    29K 29 Jan 10:52 sample.mesh
mosser@azrael generator %
```

### Visualizer

To visualize an existing mesh, go the the `visualizer` directory, and use `java -jar` to run the product. The product take two arguments (so far): the file containing the mesh, and the name of the file to store the visualization (as an SVG image).

```
mosser@azrael A2 % cd visualizer
mosser@azrael visualizer % java -jar visualizer.jar ../generator/sample.mesh sample.svg

... (lots of debug information printed to stdout) ...

mosser@azrael visualizer % ls -lh sample.svg
-rw-r--r--  1 mosser  staff    56K 29 Jan 10:53 sample.svg
mosser@azrael visualizer %
```

To viualize the SVG file:

-   Open it with a web browser
-   Convert it into something else with tool slike `rsvg-convert`

## How to contribute to the project

When you develop features and enrich the product, remember that you have first to `package` (as in `mvn package`) it so that the `jar` file is re-generated by maven.

## Backlog

### Definition of Done

-- The criteria that will decide that a feauture is done is if it functions according to the instructions and works smoothly with all the other features --

### Product Backlog

| Id  | Feature title                                       | Who?  | Start    | End      | Status |
| :-: | --------------------------------------------------- | ----- | -------- | -------- | ------ |
| F01 | Creating a minimal mesh ADT                         | Mahad | 7/02/23  | 20/02/23 | D      |
| F02 | Producing full meshes                               | Saad  | 11/02/23 | 22/02/23 | D      |
| F03 | Playing with rendering (Setting colors/thicknesses) | Ali   | 11/02/23 | 21/02/23 | D      |
| F04 | Visualization mode                                  | Ali   | 15/02/23 | 23/02/23 | D      |
