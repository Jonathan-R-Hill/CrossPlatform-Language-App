import { useRouter, useFocusEffect } from "expo-router";
import { useCallback } from "react";
import { View, Text } from "react-native";

export default function Index() {
  const router = useRouter();

  useFocusEffect(
    useCallback(() => {
      router.replace("./HomeScreen/Home");
    }, [router])
  );

  return (
    <View style={{ flex: 1, justifyContent: "center", alignItems: "center" }}>
      <Text>Redirecting to Home Screen</Text>
    </View>
  );
}
