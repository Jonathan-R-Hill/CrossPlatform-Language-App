import React, { useState } from "react";
import { View, Text, TouchableOpacity, StyleSheet } from "react-native";
import { useRouter, useLocalSearchParams } from "expo-router";

// Define navigation pages and their corresponding paths
const pages = {
  Home: "/homeScreen/home",
  "Practice Selection": "/practiceSection/practiceHome",
  "Words Management": "/wordSection/wordHome",
};

const Navbar: React.FC = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const router = useRouter();
  const { username, userId } = useLocalSearchParams();

  // Function to navigate to a selected page
  const navigateTo = (pageKey: keyof typeof pages) => {
    const path = pages[pageKey];
    router.push({ pathname: path as any, params: { username, userId } });
    setIsMenuOpen(false);
  };

  // Define the main navigation items
  const mainItems = ["Home", "Practice Selection", "Words Management"];

  return (
    <View style={styles.navbarContainer}>
      {/* Main navigation buttons */}
      <View style={styles.mainButtonsContainer}>
        {mainItems.map((item) => (
          <TouchableOpacity
            key={item}
            onPress={() => navigateTo(item as keyof typeof pages)}
            style={styles.navButton}
          >
            <Text style={styles.navButtonText}>{item}</Text>
          </TouchableOpacity>
        ))}

        {/* More button to open dropdown menu */}
        <TouchableOpacity onPress={() => setIsMenuOpen(!isMenuOpen)} style={styles.navButton}>
          <Text style={styles.navButtonText}>Other</Text>
        </TouchableOpacity>
      </View>

      {/* Dropdown menu for additional pages */}
      {isMenuOpen && (
        <View style={styles.popoutMenu}>
          {Object.keys(pages).map((pageKey) => {
            if (!mainItems.includes(pageKey)) {
              return (
                <TouchableOpacity
                  key={pageKey}
                  onPress={() => navigateTo(pageKey as keyof typeof pages)}
                  style={styles.menuItem}
                >
                  <Text style={styles.menuItemText}>{pageKey}</Text>
                </TouchableOpacity>
              );
            }
            return null;
          })}
        </View>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  navbarContainer: {
    position: "absolute",
    bottom: 0,
    left: 0,
    right: 0,
    backgroundColor: "#65558F",
    borderTopWidth: 1,
    borderTopColor: "#ccc",
    paddingTop: 1,
    marginTop: 30,
    zIndex: 20,
  },
  mainButtonsContainer: {
    flexDirection: "row",
    justifyContent: "space-evenly",
    alignItems: "center",
    paddingVertical: 10,
  },
  navButton: {
    alignItems: "center",
    justifyContent: "center",
    paddingHorizontal: 10,
  },
  navButtonText: {
    color: "#fff",
    fontSize: 12,
    marginTop: 4,
  },
  popoutMenu: {
    position: "absolute",
    bottom: "100%",
    left: 0,
    right: 0,
    backgroundColor: "#65558F",
    paddingVertical: 10,
    borderTopLeftRadius: 15,
    borderTopRightRadius: 15,
    elevation: 5,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: -2 },
    shadowOpacity: 0.25,
    shadowRadius: 3.84,
  },
  menuItem: {
    flexDirection: "row",
    alignItems: "center",
    paddingVertical: 10,
    paddingHorizontal: 20,
  },
  menuItemText: {
    color: "#fff",
    marginLeft: 15,
    fontSize: 16,
  },
});

export default Navbar;
