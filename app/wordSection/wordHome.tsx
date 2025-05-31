import { View, Text, StyleSheet } from "react-native";
import Navbar from "../../components/NavigationBar";

export default function Words() {
  return (
    <View style={styles.container}>
      <Text style={styles.text}>📚 Words</Text>
      <Navbar />
    </View>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, justifyContent: "center", alignItems: "center" },
  text: { fontSize: 24 },
});
