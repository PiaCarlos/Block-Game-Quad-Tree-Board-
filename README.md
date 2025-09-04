# Block Game (Quad-Tree Board)

## Overview
A Java-based **puzzle game framework** using a quad-tree structure to represent a square game board subdivided into colored blocks. Players can subdivide, rotate, reflect, and smash blocks, with scoring based on perimeter goals or largest connected blobs of color.  

## Features
- **Quad-tree Board Representation**: recursive block structure with position, size, and depth tracking.  
- **Random Board Generation**: blocks subdivide probabilistically with color assignment.  
- **Block Operations**:  
  - Reflect (x- or y-axis)  
  - Rotate (clockwise/counter-clockwise)  
  - Smash (replace with new random sub-blocks)  
- **Flattening & Drawing**: convert the block tree into a 2D color grid for display.  
- **Scoring System**:  
  - *Perimeter Goal*: counts unit cells of a target color along the perimeter.  
  - *Blob Goal*: finds the largest connected region of the target color.  

## Technologies
- Java  
- Object-Oriented Design (OOP)  
- Recursive algorithms & custom data structures  
